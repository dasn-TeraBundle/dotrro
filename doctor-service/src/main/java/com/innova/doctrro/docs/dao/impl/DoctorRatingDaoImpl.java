package com.innova.doctrro.docs.dao.impl;

import com.innova.doctrro.common.beans.DoctorRating;
import com.innova.doctrro.docs.dao.DoctorRatingDao;
import com.innova.doctrro.docs.dao.repository.DoctorRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.innova.doctrro.common.constants.ExceptionMessageConstants.UNSUPPORTED_OPERATIONS_MESSAGE;


@Component
public class DoctorRatingDaoImpl implements DoctorRatingDao {

    private final DoctorRatingRepository doctorRatingRepository;

    @Autowired
    public DoctorRatingDaoImpl(DoctorRatingRepository doctorRatingRepository) {
        this.doctorRatingRepository = doctorRatingRepository;
    }

    @Override
    public DoctorRating create(DoctorRating item) {
        return doctorRatingRepository.insert(item);
    }

    @Override
    public List<DoctorRating> findAllByDoctorRegId(String regId) {
        return doctorRatingRepository.findAllByDoctor_RegId(regId)
                .sorted(Comparator.comparing(DoctorRating::getCreatedDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Byte> findAllRatingByDoctor_RegId(String regId) {
        return doctorRatingRepository.findAllRatingByDoctor_RegId(regId)
                .map(DoctorRating::getRating)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorRating> findAllByRatedByEmail(String email) {
        return doctorRatingRepository.findAllByRatedBy_Email(email)
                .sorted(Comparator.comparing(DoctorRating::getCreatedDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public DoctorRating findById(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public List<DoctorRating> findAll() {
        return doctorRatingRepository.findAll();
    }

    @Override
    public DoctorRating update(String s, DoctorRating item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove(String s) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove(DoctorRating item) {
        throw new UnsupportedOperationException(UNSUPPORTED_OPERATIONS_MESSAGE);
    }

    @Override
    public void remove() {
        doctorRatingRepository.deleteAll();
    }
}
