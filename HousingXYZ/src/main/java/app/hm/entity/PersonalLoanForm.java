package app.hm.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "LoanApplication")
public class PersonalLoanForm {
	@Id
	private Long id;
	
    private String nameBn;
    private String nameEn;
    private String officeBn;
    private String fatherBn;
    private String motherBn;
    private String districtBn;
    private LocalDate joinDate;
    private String designation;
    private String nid;

    private String salaryScale;
    private Double basicSalary;
    private Double deduction;
    private Double netSalary;

    private String officeAddress;
    private String homeAddress;
    private String mobile;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNameBn() {
		return nameBn;
	}
	public void setNameBn(String nameBn) {
		this.nameBn = nameBn;
	}
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getOfficeBn() {
		return officeBn;
	}
	public void setOfficeBn(String officeBn) {
		this.officeBn = officeBn;
	}
	public String getFatherBn() {
		return fatherBn;
	}
	public void setFatherBn(String fatherBn) {
		this.fatherBn = fatherBn;
	}
	public String getMotherBn() {
		return motherBn;
	}
	public void setMotherBn(String motherBn) {
		this.motherBn = motherBn;
	}
	public String getDistrictBn() {
		return districtBn;
	}
	public void setDistrictBn(String districtBn) {
		this.districtBn = districtBn;
	}
	public LocalDate getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getSalaryScale() {
		return salaryScale;
	}
	public void setSalaryScale(String salaryScale) {
		this.salaryScale = salaryScale;
	}
	public Double getBasicSalary() {
		return basicSalary;
	}
	public void setBasicSalary(Double basicSalary) {
		this.basicSalary = basicSalary;
	}
	public Double getDeduction() {
		return deduction;
	}
	public void setDeduction(Double deduction) {
		this.deduction = deduction;
	}
	public Double getNetSalary() {
		return netSalary;
	}
	public void setNetSalary(Double netSalary) {
		this.netSalary = netSalary;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
    
    
}
