package com.apap.tugas1.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;


@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long> {
	PegawaiModel findById(long id);
	PegawaiModel findByNip (String nip);
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	List<PegawaiModel> findByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findByListJabatan(JabatanModel jabatan);
	List<PegawaiModel> findByTanggalLahirAndInstansiAndTahunMasuk(Date tanggalLahir, InstansiModel instansi, String tahunMasuk);
	String deleteByNip(String nip);
	
}