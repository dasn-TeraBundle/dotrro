package com.innova.doctrro.bs.service;

import com.innova.doctrro.bs.beans.BookingSlot;
import com.innova.doctrro.common.service.GenericService;

import java.util.List;

public interface BookingSlotService extends GenericService<BookingSlot, BookingSlot, String> {

    List<BookingSlot> create(List<BookingSlot> items);

}
