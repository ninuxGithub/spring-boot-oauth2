//package com.example.demo.entity;
//
//import java.io.Serializable;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//
//@Entity
//@Table(name="security_user_role")
//public class UserRole implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -3538282563141610048L;
//	
//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	private Long id;
//
//	@Column(name="user_id")
//	private Long userId;
//
//	@Column(name="role_id")
//	private Long roleId;
//
//
//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}
//
//	public Long getRoleId() {
//		return roleId;
//	}
//
//	public void setRoleId(Long roleId) {
//		this.roleId = roleId;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	@Override
//	public String toString() {
//		return "UserRole [userId=" + userId + ", roleId=" + roleId + ", id=" + id + "]";
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
//		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
//		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		UserRole other = (UserRole) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		if (roleId == null) {
//			if (other.roleId != null)
//				return false;
//		} else if (!roleId.equals(other.roleId))
//			return false;
//		if (userId == null) {
//			if (other.userId != null)
//				return false;
//		} else if (!userId.equals(other.userId))
//			return false;
//		return true;
//	}
//
//}
