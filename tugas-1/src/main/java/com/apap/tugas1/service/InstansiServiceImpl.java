package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	@Autowired
	private InstansiDb instansiDb;

	@Override
	public List<InstansiModel> getAllInstansi() {
		return instansiDb.findAll();
	}
	
	
	@Override
	public InstansiModel getInstansiById(long id) {
		// TODO Auto-generated method stub
		return instansiDb.findById(id);
	}

//	@Override
//	public List<InstansiModel> getInstansiFromProvinsi(ProvinsiModel provinsi) {
//		return instansiDb.findByProvinsi(provinsi);
//	}
	
	@Override
	public Optional<InstansiModel> getInstansiById(Long id) {
		return instansiDb.findById(id);
	}
	
	@Override
	public List<InstansiModel> getInstansiByProvinsi(ProvinsiModel provinsi) {
		return instansiDb.findByProvinsi(provinsi);
	}
}
