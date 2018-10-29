package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		return "add-success";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value ="id") Long id, Model model) {
		JabatanModel oldJabatan = jabatanService.getJabatanDetailById(id);
		model.addAttribute("jabatan", oldJabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(Model model, @ModelAttribute JabatanModel jabatan) {
		jabatanService.updateJabatan(jabatan.getId() , jabatan);
		return "update-success";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewJabatan(@RequestParam("id") Long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		List<PegawaiModel> pegawai = pegawaiService.getPegawaiByJabatan(jabatan);
		int jlhPegawai = pegawai.size();
		model.addAttribute("jlhPegawai", jlhPegawai);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";

	}
	/*
	 * hanya dapat menghapus jabatan pegawai yang tidak memiliki pegawai
	 */
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String deleteJabatan(@RequestParam( value = "id") long id, Model model) {
		JabatanModel jabatanInput = jabatanService.getJabatanDetailById(id);
		try {
			jabatanService.deleteJabatanById(id);
			return "delete-success";
		}
		catch (Exception e) {
			return "delete-fail";
		}
	}
	@RequestMapping(value = "/jabatan/viewall", method = RequestMethod.GET)
	private String viewAllJabatan(Model model) {
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		return "view-all-jabatan";
	}
	
}