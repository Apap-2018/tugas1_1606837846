package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.model.ProvinsiModel;


@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		return provinsiDb.findAll();
	}
	
	@Override
	public ProvinsiModel getProvinsiDetailById(long id) {
		return provinsiDb.findById(id);
	}

}
