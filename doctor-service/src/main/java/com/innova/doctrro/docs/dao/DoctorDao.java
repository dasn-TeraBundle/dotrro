package com.innova.doctrro.docs.dao;

import com.innova.doctrro.common.dao.GenericDao;
import com.innova.doctrro.common.beans.Doctor;

public interface DoctorDao extends GenericDao<Doctor, String> {

    Doctor findByEmail(String email);
}
