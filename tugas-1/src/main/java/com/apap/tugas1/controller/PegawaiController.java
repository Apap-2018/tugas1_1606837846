package com.apap.tugas1.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		List<ProvinsiModel> provinsiList = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", provinsiList);
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		//List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(listProv.get(0));
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setListJabatan(new ArrayList<JabatanModel>());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"addRow"}, method = RequestMethod.POST)
	public String addRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRow(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		

		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		System.out.println(rowId);
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String kode = String.valueOf(pegawai.getInstansi().getId());
		
		SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
		String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
		
		String tahunKerja = pegawai.getTahunMasuk();

		int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
		
		String strUrutan;
		if(urutan<10) strUrutan="0"+urutan;
		else strUrutan=""+urutan;
		
		String nip = kode + tanggalLahir + tahunKerja + strUrutan;
		
		pegawai.setNip(nip);
		
		pegawaiService.addPegawai(pegawai);
		
		String msg = "Pegawai dengan NIP "+ nip +" berhasil ditambahkan";
		model.addAttribute("message", msg);
		return "pegawai-success";
	}
	
	@RequestMapping(value="/pegawai/ubah", method = RequestMethod.GET)
	public String updatePegawai(@RequestParam("nip") String nip, Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);

		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
	    model.addAttribute("pegawai", pegawai);
	    return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"addRow"}, method = RequestMethod.POST)
	public String addRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		ProvinsiModel provinsi = pegawai.getInstansi().getProvinsi();
		model.addAttribute("selectedItem", provinsi);
		
		
		pegawai.getListJabatan().add(new JabatanModel());
	    model.addAttribute("pegawai", pegawai);
	    return "update-pegawai";
	}
	
	@RequestMapping(value="/pegawai/ubah", params={"deleteRow"}, method = RequestMethod.POST)
	public String deleteRowUpdate(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, HttpServletRequest req,Model model) {
		
		List<ProvinsiModel> listProv = provinsiService.getAllProvinsi();
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProv);
		
		List<InstansiModel> listInstansi = instansiService.getInstansiByProvinsi(pegawai.getInstansi().getProvinsi());
		model.addAttribute("listInstansi", listInstansi);
		
		Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
		pegawai.getListJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
	    return "update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String oldNip = pegawai.getNip();
		PegawaiModel oldPegawai = pegawaiService.getPegawaiDetailByNip(oldNip);
		
		String newNip;
		if((!oldPegawai.getTahunMasuk().equals(pegawai.getTahunMasuk())) || 
				(!oldPegawai.getTanggalLahir().equals(pegawai.getTanggalLahir())) || 
				(!oldPegawai.getInstansi().equals(pegawai.getInstansi()))) {
			
			String kode = String.valueOf(pegawai.getInstansi().getId());
			
			SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yy");
			String tanggalLahir = newFormat.format(pegawai.getTanggalLahir()).replaceAll("-", "");
			
			String tahunKerja = pegawai.getTahunMasuk();
			
			int urutan = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk()).size()+1;
			
			String strUrutan;
			if(urutan<10) strUrutan="0"+urutan;
			else strUrutan=""+urutan;
			
			newNip = kode + tanggalLahir + tahunKerja + strUrutan;
			pegawai.setNip(newNip);
		}
		else {
			 newNip = oldNip;
			 pegawai.setNip(oldNip);
		}
		
		
		pegawaiService.updatePegawai(oldNip, pegawai);
		
		String msg = "Pegawai dengan NIP "+ newNip +" berhasil diubah";
		model.addAttribute("message", msg);
		return "pegawai-success";
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