package com.innova.doctrro.docs.dao;

import com.innova.doctrro.common.dao.GenericDao;
import com.innova.doctrro.docs.beans.DoctorRating;

import java.util.List;
import java.util.stream.Stream;

public interface DoctorRatingDao extends GenericDao<DoctorRating, String> {

    List<DoctorRating> findAllByDoctorRegId(String regId);
    List<Byte> findAllRatingByDoctor_RegId(String regId);
    List<DoctorRating> findAllByRatedByEmail(String email);
}
