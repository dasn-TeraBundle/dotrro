package com.innova.doctrro.bs.dao;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.common.dao.GenericDao;

import java.util.List;

public interface BookingSlotDao extends GenericDao<BookingSlot, String> {

    List<BookingSlot> create(List<BookingSlot> slots);
}
