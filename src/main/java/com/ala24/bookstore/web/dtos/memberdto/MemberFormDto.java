package com.ala24.bookstore.web.dtos.memberdto;

import com.ala24.bookstore.domain.Address;
import com.ala24.bookstore.domain.Cash;
import com.ala24.bookstore.domain.Member;
import com.ala24.bookstore.domain.strategy.InitialCashStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class MemberFormDto {

	@NotBlank
	private String loginId;

	@NotBlank
	@Size(min = 4, max = 16)
	private String password;

	@NotBlank
	private String name;

	private String city;
	private String specificAddress;
	private Integer zipcode;

	public MemberFormDto() {

	}

	public Member toEntity() {
		return Member.builder()
				.name(this.name)
				.loginId(this.loginId)
				.password(this.password)
				.cash(Cash.charge(InitialCashStrategy.NORMAl.getCash()))
				.address(Address.builder()
						.specificAddress(this.specificAddress)
						.city(this.city)
						.zipCode(this.zipcode)
						.build())
				.build();
	}
}
