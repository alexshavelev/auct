package com.softserve.auction.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.auction.dao.Dao;
import com.softserve.auction.domain.Lot;
import com.softserve.auction.domain.LotStates;
import com.softserve.auction.domain.UploadFile;
import com.softserve.auction.domain.User;

@Service
@Transactional
public class LotService {
	@Resource
	private Dao<Lot> lotDaoImpl;
	@Resource
	private UserService userService;
	@Resource
	private PaymentService paymentService;
	private int pageSize = 6;
	@Resource
	private FileService fileService;

	public String saveLot(Map<String, String[]> param, User user, UploadFile img,
			String path) {
		String name = ((String[]) param.get("name"))[0];
		String enddateStr = ((String[]) param.get("enddate"))[0];
		String hours = ((String[]) param.get("hour"))[0];
		String min = ((String[]) param.get("min"))[0];
		String description = ((String[]) param.get("description"))[0];
		String sp = ((String[]) param.get("startprice"))[0];
		BigDecimal startPrice = new BigDecimal(0);

		if (!sp.isEmpty()) {
			startPrice = new BigDecimal(sp);
		}
		Calendar endDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date;
		try {
			date = formatter.parse(enddateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return "Invalid date format";
		}
		endDate.setTime(date);
		endDate.set(Calendar.HOUR, Integer.parseInt(hours));
		endDate.set(Calendar.MINUTE, Integer.parseInt(min));
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		if (endDate.before(now)) {
			return "End date should be later than the current!";
		}
		Lot lot = new Lot();
		lot.setName(name);
		lot.setEndDate(endDate);
		lot.setDescription(description);
		lot.setStartPrice(startPrice);
		lot.setUser(user);
		lot.setImgPath("/resources/userimg/" + img.getFile().getOriginalFilename());
		fileService.saveFile(path, img);
		user.getLots().add(lot);
		userService.updateUser(user.getUserId());
		lotDaoImpl.save(lot);
		return "Saved";
	}

	public void addLot(Lot lot) {
		lotDaoImpl.save(lot);
	}

	public List<Lot> findAllLots() {
		return lotDaoImpl.findAll();
	}

	public List<Lot> findLots(String query) {
		return lotDaoImpl.findByQuery(query);
	}

	public List<Lot> findByPage(int page, String query) {
		int firstResult = page * pageSize - pageSize;
		return lotDaoImpl.findByPage(firstResult, pageSize, query);
	}

	public void updateLot(Long id) {
		lotDaoImpl.update(id);
	}

	public void updateLotByQuery(String query) {
		lotDaoImpl.updateByQuery(query);
	}

	public boolean deleteLot(Long id) {
		Lot lot = findById(id);
		if (lot != null && lot.getLotState() != LotStates.PUBLISHED
				&& lot.getBets().isEmpty()) {
			lotDaoImpl.delete(id);
			return true;
		}
		return false;
	}

	@Scheduled(fixedRate = 30000)
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void checkLotState() {
		String query = "UPDATE lot SET lotstate=2 WHERE endDate<NOW() AND currentBet_id>0 AND lotstate<2";
		lotDaoImpl.updateByQuery(query);
		paymentService.selling();
	}

	public Lot findById(Long lotId) {
		return lotDaoImpl.findById(lotId);
	}
}
