package com.softserve.auction.service;

import org.springframework.stereotype.Service;

@Service
public class PageService {
	public String buildPageNav(String path, int resultSize, int page, int pageSize, int pageNavTrail) {
        int allPage = resultSize / pageSize;
        boolean isOdd = (resultSize % pageSize != 0);
        allPage = (isOdd ? allPage+1 : allPage);
        int iStart = page - pageNavTrail;
        int iEnd = page + pageNavTrail;
        if(iStart < 1) {
            iEnd = iEnd + (1-iStart);
            iStart = 1;
            if(iEnd > allPage) {
                iEnd = allPage;
            }
        } else if(iEnd > allPage) {
            iStart = iStart - (iEnd - allPage);
            iEnd = allPage;
            if(iStart < 1) {
                iStart = 1;
            }
        }
        StringBuffer sb = new StringBuffer();
        for(int i=iStart; i <=iEnd; i++) {
            if(i == page) {
                sb.append("<span class=\"current_page\">").append(page).append("</span>").append(" ");
            }
            else {
                sb.append("<a href='").append(path).append(i).append("'>").append(i).append("</a>").append(" ");
            }
        }
        return sb.toString();
    }
}
