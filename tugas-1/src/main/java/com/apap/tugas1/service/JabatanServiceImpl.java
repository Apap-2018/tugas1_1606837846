package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	

	@Override
	public JabatanModel getJabatanDetailById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}
	
	@Override
	public List<JabatanModel> getAllJabatan() {
		return jabatanDb.findAll();
	}

	@Override
	public void updateJabatan(Long id, JabatanModel newJabatan) {
		JabatanModel oldJabatan = this.getJabatanDetailById(id);
		oldJabatan.setNama(newJabatan.getNama());
		oldJabatan.setDeskripsi(newJabatan.getDeskripsi());
		oldJabatan.setGajiPokok(newJabatan.getGajiPokok());
	}
	
	@Override
	public void deleteJabatanById (long id) {
		jabatanDb.deleteById(id);
	}
	
	@Override
	public Optional<JabatanModel> getJabatanById(Long id) {
		return jabatanDb.findById(id);
	}
}
