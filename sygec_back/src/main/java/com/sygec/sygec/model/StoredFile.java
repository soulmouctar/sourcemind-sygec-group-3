package com.sygec.sygec.model;

import lombok.Builder;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Builder
public class StoredFile extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private String type;
    @Column(length = 100000)
    private byte[] bytes;
    
    @Column(length = 100000)
    private String data;
    
    public StoredFile() {
        super();
    }

    public StoredFile(String name, String type, byte[] bytes, String data) {
        this.name = name;
        this.type = type;
        this.data = data;
        this.bytes = bytes;
    }

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

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StoredFile))
            return false;
        StoredFile employee = (StoredFile) o;
        return Objects.equals(this.uuid, employee.uuid) && Objects.equals(this.name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid, this.name);
    }

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	

}
