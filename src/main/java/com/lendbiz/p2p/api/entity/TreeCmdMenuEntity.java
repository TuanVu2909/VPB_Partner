package com.lendbiz.p2p.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.ToString;

@ToString
public class TreeCmdMenuEntity implements Serializable{

	private static final long serialVersionUID = 1L;
    
	List<TreeCmdMenuEntity> menuChildrens;

    private String cmdId;
    private String prId;
    private int lev;
    private String last;
    private String menutype;
    private String menucode;
    private String modCode;
    private String objName;
    private String cmdName;
    private String en_cmdName;
    private String authCode;
    private String tltxCd;


	public TreeCmdMenuEntity() {
		this.menuChildrens = new ArrayList<TreeCmdMenuEntity>();
	}

	public TreeCmdMenuEntity(List<TreeCmdMenuEntity> menuChildrens, String cmdId, String prId, int lev, String last, String menutype, String menucode, String modCode, String objName, String cmdName, String en_cmdName, String authCode, String tltxCd) {
		this.menuChildrens = menuChildrens;
		this.cmdId = cmdId;
		this.prId = prId;
		this.lev = lev;
		this.last = last;
		this.menutype = menutype;
		this.menucode = menucode;
		this.modCode = modCode;
		this.objName = objName;
		this.cmdName = cmdName;
		this.en_cmdName = en_cmdName;
		this.authCode = authCode;
		this.tltxCd = tltxCd;
	}

	public List<TreeCmdMenuEntity> getMenuChildrens() {
		return this.menuChildrens;
	}

	public void setMenuChildrens(List<TreeCmdMenuEntity> menuChildrens) {
		this.menuChildrens = menuChildrens;
	}

	public String getCmdId() {
		return this.cmdId;
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public String getPrId() {
		return this.prId;
	}

	public void setPrId(String prId) {
		this.prId = prId;
	}

	public int getLev() {
		return this.lev;
	}

	public void setLev(int lev) {
		this.lev = lev;
	}

	public String getLast() {
		return this.last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public String getMenutype() {
		return this.menutype;
	}

	public void setMenutype(String menutype) {
		this.menutype = menutype;
	}

	public String getMenucode() {
		return this.menucode;
	}

	public void setMenucode(String menucode) {
		this.menucode = menucode;
	}

	public String getModCode() {
		return this.modCode;
	}

	public void setModCode(String modCode) {
		this.modCode = modCode;
	}

	public String getObjName() {
		return this.objName;
	}

	public void setObjName(String objName) {
		this.objName = objName;
	}

	public String getCmdName() {
		return this.cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public String getEn_cmdName() {
		return this.en_cmdName;
	}

	public void setEn_cmdName(String en_cmdName) {
		this.en_cmdName = en_cmdName;
	}

	public String getAuthCode() {
		return this.authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getTltxCd() {
		return this.tltxCd;
	}

	public void setTltxCd(String tltxCd) {
		this.tltxCd = tltxCd;
	}

	public TreeCmdMenuEntity menuChildrens(List<TreeCmdMenuEntity> menuChildrens) {
		setMenuChildrens(menuChildrens);
		return this;
	}

	public TreeCmdMenuEntity cmdId(String cmdId) {
		setCmdId(cmdId);
		return this;
	}

	public TreeCmdMenuEntity prId(String prId) {
		setPrId(prId);
		return this;
	}

	public TreeCmdMenuEntity lev(int lev) {
		setLev(lev);
		return this;
	}

	public TreeCmdMenuEntity last(String last) {
		setLast(last);
		return this;
	}

	public TreeCmdMenuEntity menutype(String menutype) {
		setMenutype(menutype);
		return this;
	}

	public TreeCmdMenuEntity menucode(String menucode) {
		setMenucode(menucode);
		return this;
	}

	public TreeCmdMenuEntity modCode(String modCode) {
		setModCode(modCode);
		return this;
	}

	public TreeCmdMenuEntity objName(String objName) {
		setObjName(objName);
		return this;
	}

	public TreeCmdMenuEntity cmdName(String cmdName) {
		setCmdName(cmdName);
		return this;
	}

	public TreeCmdMenuEntity en_cmdName(String en_cmdName) {
		setEn_cmdName(en_cmdName);
		return this;
	}

	public TreeCmdMenuEntity authCode(String authCode) {
		setAuthCode(authCode);
		return this;
	}

	public TreeCmdMenuEntity tltxCd(String tltxCd) {
		setTltxCd(tltxCd);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof TreeCmdMenuEntity)) {
			return false;
		}
		TreeCmdMenuEntity TreeCmdMenuEntity = (TreeCmdMenuEntity) o;
		return Objects.equals(menuChildrens, TreeCmdMenuEntity.menuChildrens) && Objects.equals(cmdId, TreeCmdMenuEntity.cmdId) && Objects.equals(prId, TreeCmdMenuEntity.prId) && lev == TreeCmdMenuEntity.lev && Objects.equals(last, TreeCmdMenuEntity.last) && Objects.equals(menutype, TreeCmdMenuEntity.menutype) && Objects.equals(menucode, TreeCmdMenuEntity.menucode) && Objects.equals(modCode, TreeCmdMenuEntity.modCode) && Objects.equals(objName, TreeCmdMenuEntity.objName) && Objects.equals(cmdName, TreeCmdMenuEntity.cmdName) && Objects.equals(en_cmdName, TreeCmdMenuEntity.en_cmdName) && Objects.equals(authCode, TreeCmdMenuEntity.authCode) && Objects.equals(tltxCd, TreeCmdMenuEntity.tltxCd);
	}

	@Override
	public int hashCode() {
		return Objects.hash(menuChildrens, cmdId, prId, lev, last, menutype, menucode, modCode, objName, cmdName, en_cmdName, authCode, tltxCd);
	}

	@Override
	public String toString() {
		return "{" +
			" menuChildrens='" + getMenuChildrens() + "'" +
			", cmdId='" + getCmdId() + "'" +
			", prId='" + getPrId() + "'" +
			", lev='" + getLev() + "'" +
			", last='" + getLast() + "'" +
			", menutype='" + getMenutype() + "'" +
			", menucode='" + getMenucode() + "'" +
			", modCode='" + getModCode() + "'" +
			", objName='" + getObjName() + "'" +
			", cmdName='" + getCmdName() + "'" +
			", en_cmdName='" + getEn_cmdName() + "'" +
			", authCode='" + getAuthCode() + "'" +
			", tltxCd='" + getTltxCd() + "'" +
			"}";
	}


}
