package com.kh_sof_dev.gaz.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class pagatination {
    private int size,totalElements,totalPages,pageNumber;
    public pagatination(JSONObject jsonObject) throws JSONException {
        if(jsonObject == null){
            return;
        }
        size = jsonObject.optInt("size");
        totalElements = jsonObject.optInt("totalElements");
        totalPages = jsonObject.optInt("totalPages");
        pageNumber = jsonObject.optInt("pageNumber");}

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
