package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(Long id, JabatanModel jabatan);
	JabatanModel getJabatanDetailById(long id);
	List<JabatanModel> getAllJabatan();
	void deleteJabatanById(long id);
	Optional<JabatanModel> getJabatanById(Long id);
}
