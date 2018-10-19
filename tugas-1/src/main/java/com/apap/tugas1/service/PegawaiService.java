package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailById(long id);
	PegawaiModel getPegawaiDetailByNip (String nip);
	void addPegawai(PegawaiModel pegawai);
	List<PegawaiModel> getTuaMudaInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	List<PegawaiModel> getPegawaiByProvinsiAndJabatan(Long provinsiId, JabatanModel jabatan);
	List<PegawaiModel> getPegawaiByProvinsi(Long provinsiId);
	List<PegawaiModel> getPegawaiByJabatan(JabatanModel jabatan);
	
}