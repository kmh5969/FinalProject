package com.prj.te.model.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prj.te.dto.MemberDto;

@Repository
public class MemberDaoImpl implements MemberDao {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired 
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public MemberDto login(String id) {
		MemberDto dto = null;
		try {
			dto =sqlSession.selectOne(namespace+"login", id);
		} catch (Exception e) {
		System.out.println("LOGIN ERROR");
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public List<MemberDto> selectList() {
		List<MemberDto> list = null;
		
		try {
			list = sqlSession.selectList(namespace+"selectList");
		} catch (Exception e) {
			System.out.println("SELECTLIST ERROR");
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public MemberDto selectone(int member_seq) {
		MemberDto dto = null;
		
		try {
			dto = sqlSession.selectOne(namespace+"selectOne",member_seq);
		} catch (Exception e) {
			System.out.println("SELECTONE ERROR");
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	@Transactional
	public int insert(MemberDto dto) {
		int res = 0;
		
		try {
			res = sqlSession.insert(namespace+"insert",dto);
			
		} catch (Exception e) {
			System.out.println("INSERT ERROR");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	@Transactional
	public int update(MemberDto dto) {
		int res = 0;
		
		try {
			res = sqlSession.update(namespace+"update",dto);
		} catch (Exception e) {
			System.out.println("UPDATE ERROR");
			e.printStackTrace();
		}
		
		return res;
	}

	@Override
	@Transactional
	public int delete(int member_seq) {
		int res = 0;
		
		try {
			res = sqlSession.update(namespace+"delete",member_seq);
		} catch (Exception e) {
			System.out.println("DELETE ERROR");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public MemberDto findAccount(String email , String id) {
		MemberDto user = null;
		user.setId(id);
		user.setEmail(email);
		try {
			user = sqlSession.selectOne(namespace+"findAccount",email);
		} catch (Exception e) {
			System.out.println("FINDACCOUNT ERROR");
			e.printStackTrace();
		}
		return user;
	}
	@Override
	public int updateInfo(String id, String pw, String email) {
		MemberDto dto = new MemberDto();
		if(pw!=null && pw!="") {
			dto.setPw(passwordEncoder.encode(pw));
		}
		
		Map<String,String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("pw", dto.getPassword());
		map.put("email", email);
		
		int res = 0;
		try {
			res = sqlSession.update(namespace+"updateInfo", map);
		} catch (Exception e) {
			System.out.println("FINDACCOUNT ERROR");
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean pwchk(String username, String pw) {
			MemberDto dto = null;
		
		try {
			dto = sqlSession.selectOne(namespace+"login",username);
		} catch (Exception e) {
			System.out.println("PWCHK ERROR");
			e.printStackTrace();
		}
		return passwordEncoder.matches(pw, dto.getPw());
	
	}

	@Override
	public int idChk(String id) {
		int res = 0;
				
		try {
			res = sqlSession.selectOne(namespace+"idChk",id); 
		} catch (Exception e) {
			System.out.println("IDCHK ERROR");
		}
		return res;
	}

	@Override
	public MemberDto selectoneId(String id) {
		MemberDto dto = null;
		try {
			dto = sqlSession.selectOne(namespace+"selectOneId", id);
		} catch (Exception e) {
			System.out.println("SELECTONE ERROR");
			e.printStackTrace();
		}
		return dto;
	} 
}
