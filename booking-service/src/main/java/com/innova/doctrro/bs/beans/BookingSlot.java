package com.innova.doctrro.bs.beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "booking_slots")
@CompoundIndex(name = "fid_did_st", def = "{'facilityId' : 1, 'doctorId': 1, 'startTime': 1}", unique = true)
public class BookingSlot {

    @Id
    private String id;
    private String facilityId;
    private String doctorId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean bookingEnabled;
    private SlotStatus status;

    @Version
    private long version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingSlot that = (BookingSlot) o;

        if (!facilityId.equals(that.facilityId)) return false;
        if (!doctorId.equals(that.doctorId)) return false;
        return startTime.equals(that.startTime);
    }

    @Override
    public int hashCode() {
        int result = facilityId.hashCode();
        result = 31 * result + doctorId.hashCode();
        result = 31 * result + startTime.hashCode();
        return result;
    }
}
