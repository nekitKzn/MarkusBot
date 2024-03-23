package com.example.markusbot.service;

import com.example.markusbot.model.FileEntity;
import com.example.markusbot.model.SettingEntity;
import com.example.markusbot.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    @Transactional
    public SettingEntity getSetting() {
        return settingRepository.findById(1L).orElse(null);
    }

    @Transactional
    public void updateYuan(Double yuan) {
        var setting = getSetting();
        setting.setYuanExchangeRate(yuan);
        settingRepository.save(setting);
    }

    @Transactional
    public void updateCommission(Double commission) {
        var setting = getSetting();
        setting.setCommission(commission);
        settingRepository.save(setting);
    }

    @Transactional
    public void updateMenu(FileEntity file) {
        var setting = getSetting();
        setting.setFile(file);
        settingRepository.save(setting);
    }
}
