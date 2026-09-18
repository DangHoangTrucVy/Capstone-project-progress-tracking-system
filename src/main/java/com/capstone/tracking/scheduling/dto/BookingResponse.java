package com.capstone.tracking.scheduling.dto;

import com.capstone.tracking.scheduling.Booking;
import com.capstone.tracking.scheduling.BookingStatus;

import java.time.Instant;
import java.util.UUID;

public record BookingResponse(
        UUID id,
        UUID slotId,
        UUID groupId,
        String groupCode,
        BookingStatus status,
        Instant bookedAt,
        String notes
) {
    public static BookingResponse from(Booking b) {
        return new BookingResponse(b.getId(), b.getSlot().getId(), b.getGroup().getId(),
                b.getGroup().getGroupCode(), b.getBookingStatus(), b.getBookedAt(), b.getNotes());
    }
}
