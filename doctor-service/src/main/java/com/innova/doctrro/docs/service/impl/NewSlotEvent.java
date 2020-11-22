package com.innova.doctrro.docs.service.impl;

import com.innova.doctrro.common.dto.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
class NewSlotEvent extends Event {
    private String facilityId;
    private String facilityName;

    private String doctorId;
    private String doctorName;

    private String startTime;
    private String endTime;
    private boolean bookingEnabled;

    private double charge;
    private boolean autoApproveEnabled;


    @Override
    public String toString() {
        return "NewSlotEvent{" +
                "facilityId='" + facilityId + '\'' +
                ", facilityName='" + facilityName + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", bookingEnabled=" + bookingEnabled +
                ", charge=" + charge +
                ", autoApproveEnabled=" + autoApproveEnabled +
                '}';
    }
}
