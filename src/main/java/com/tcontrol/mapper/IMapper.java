package com.tcontrol.mapper;

public interface IMapper <input, output> {
	
	public output map(input in);
}
