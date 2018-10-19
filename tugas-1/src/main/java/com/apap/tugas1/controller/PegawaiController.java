package com.apap.tugas1.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> jabatanList = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", jabatanList);
		
		List<InstansiModel> instansiList = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", instansiList);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String showDataPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		
		double gajiPokokPegawai = pegawai.getListJabatan().get(0).getGajiPokok();
		if(pegawai.getListJabatan().size() > 1) {
			for(int i=1 ; i< pegawai.getListJabatan().size(); i++) {
				if(pegawai.getListJabatan().get(i).getGajiPokok() > gajiPokokPegawai) {
					gajiPokokPegawai = pegawai.getListJabatan().get(i).getGajiPokok();
				}
			}
		}
		
		double tunjangan = pegawai.getInstansi().getProvinsi().getPersentaseTunjangan() /100;
		gajiPokokPegawai = gajiPokokPegawai + (tunjangan * gajiPokokPegawai);
		String gaji = String.format ("%.0f", gajiPokokPegawai);
		
		model.addAttribute("gaji", gaji);
		model.addAttribute("pegawai", pegawai);
		return "view-pegawai";
		
	}
	
	@RequestMapping("/pegawai/termuda-tertua")
	public String viewPegawai (@RequestParam Long idInstansi, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getTuaMudaInstansi(instansiService.getInstansiById(idInstansi).get());
		model.addAttribute("pegawaiTertua", listPegawai.get(0));
		model.addAttribute("pegawaiTermuda", listPegawai.get(listPegawai.size()-1));
		return "view-termuda-tertua";
	}
	/*
	 * 
	 * HashSet yang sama diitung satu
	 * kalo provinsi gada otomatis instansi gaada
	 * kalo ketiga komponen gada yaudah gausah dibuah
	 * kalo provinsi gada tp instansi sama jabatan ada ga guna balik ke pertama
	 * kalo provinsi gada tp instansi ada sama jabatan gada balik ke pertama
	 */
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai (@RequestParam(value="idProvinsi") Optional<Long> idProvinsi, 
								@RequestParam(value="idInstansi") Optional<Long> idInstansi, 
								@RequestParam(value="idJabatan") Optional<Long> idJabatan, Model model) {
		
		ProvinsiModel provinsi = null;
		InstansiModel instansi = null;
		JabatanModel jabatan = null;
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<PegawaiModel> hasilPencarian = null;
		if (idProvinsi.isPresent()) {
			provinsi = provinsiService.getProvinsiDetailById(idProvinsi.get());
			if (idInstansi.isPresent()) {
				instansi = instansiService.getInstansiById(idInstansi.get()).get();	
				if (idJabatan.isPresent()) {
					jabatan = jabatanService.getJabatanDetailById(idJabatan.get());
					hasilPencarian = pegawaiService.getPegawaiByInstansiAndJabatan(instansi, jabatan);
				}
				else {
					hasilPencarian = pegawaiService.getPegawaiByInstansi(instansi);
				}
			}
			else if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get()).get();	
				hasilPencarian = pegawaiService.getPegawaiByProvinsiAndJabatan(idProvinsi.get(), jabatan);
			}
			else {
				hasilPencarian = pegawaiService.getPegawaiByProvinsi(idProvinsi.get());
			}
		}
		else {
			if (idJabatan.isPresent()) {
				jabatan = jabatanService.getJabatanById(idJabatan.get()).get();	
				hasilPencarian = pegawaiService.getPegawaiByJabatan(jabatan);
			}
		}
		model.addAttribute("provinsi", provinsi);
		model.addAttribute("instansi", instansi);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("hasilPencarian", hasilPencarian);
		return "cari-pegawai";
	}	

}