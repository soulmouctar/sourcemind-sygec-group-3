package com.sygec.sygec.dto;

public class StoredFileDto {

    private String uuid;

	private String name;
	
	private String data;

	private String type;

    private byte[] bytes;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
    
    
}
