package com.example.smsbe.service;

import com.example.smsbe.entity.Manager;

public interface ManagerService {
    Manager loadUserByUsername(String username);
}
