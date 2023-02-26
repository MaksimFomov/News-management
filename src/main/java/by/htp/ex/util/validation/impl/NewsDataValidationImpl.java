package by.htp.ex.util.validation.impl;

import by.htp.ex.util.validation.NewsDataValidation;

public class NewsDataValidationImpl implements NewsDataValidation {
	
	@Override
	public boolean checkNewsData(String title, String brief, String content, String date) {
		if(title != "" && brief != "" && content != "" && date != "") {
			return true;
		}
		
		return false;
	}
}
