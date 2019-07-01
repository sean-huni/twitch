package xyz.tag.twitch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFound;
import xyz.tag.twitch.repo.DeviceRepo;
import xyz.tag.twitch.service.DeviceService;
import xyz.tag.twitch.service.RaspberryPiService;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PROJECT   : twitch
 * PACKAGE   : xyz.tag.twitch.service.impl
 * USER      : sean
 * DATE      : 03-Sun-Mar-2019
 * TIME      : 11:35
 * E-MAIL    : kudzai@bcs.org
 * CELL      : +27-64-906-8809
 */
@Service
public class DeviceServiceImpl implements DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
    private RaspberryPiService raspberryPiService;
    private DeviceRepo deviceRepo;
    private Converter<Device, DeviceDTO> toDeviceDTO;
    private Converter<DeviceDTO, Device> toDeviceDO;
    private Converter<Log, LogDTO> toLogDTO;
    private Converter<LogDTO, Log> toLogDO;


    public DeviceServiceImpl(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @Autowired
    public void setToDeviceDTO(Converter<Device, DeviceDTO> toDeviceDTO) {
        this.toDeviceDTO = toDeviceDTO;
    }

    @Autowired
    public void setToDeviceDO(Converter<DeviceDTO, Device> toDeviceDO) {
        this.toDeviceDO = toDeviceDO;
    }

    @Autowired
    public void setToLogDTO(Converter<Log, LogDTO> toLogDTO) {
        this.toLogDTO = toLogDTO;
    }

    @Autowired
    public void setToLogDO(Converter<LogDTO, Log> toLogDO) {
        this.toLogDO = toLogDO;
    }

    @Autowired
    public void setRaspberryPiService(RaspberryPiService raspberryPiService) {
        this.raspberryPiService = raspberryPiService;
    }

    /**
     * Turns the switch Onn or Off
     *
     * @param id
     * @param option
     * @return
     */
    @Override
    public void toggleSwitch(Long id, ESwitch option) throws DeviceNotFound {
        Optional<Device> optionalDevice = deviceRepo.findById(id);
        final Device device = optionalDevice.orElseThrow(() -> new DeviceNotFound("Device ID: " + id + " not found."));
        final Boolean b = option.getStatus().equals("ONN");
        device.setOnn(b);
        EStatus status = EStatus.valueOf("ONLINE");

        final Req req = new Req(option);

        LOGGER.info("HTTP POST Req: {}", req.toString());
        Resp resp = new Resp();
        final Log log = new Log(option, status, ZonedDateTime.now(), null);
        try {
            final long channel = Long.parseLong(device.getChannel());

            LOGGER.info("Extracted Device-Channel: {}", channel);

            resp = raspberryPiService.invokeDeviceSwitch(req, channel);
            if (resp.getCode() >= 200) {
                log.setEStatus(EStatus.valueOf("ONLINE"));
            } else {
                log.setEStatus(EStatus.valueOf("OFFLINE"));
            }
        } catch (Exception e) {
            LOGGER.error("HTTP Exception: {}", e.getMessage(), e);
            device.setOnn(false);
            log.setEStatus(EStatus.valueOf("UNREACHABLE"));
            log.setESwitch(ESwitch.valueOf("OFF"));
        }
        log.setRespDateTime(ZonedDateTime.now());
        LOGGER.info("HTTP POST Response: {}", resp.toString());
        device.getLogs().add(log);
        deviceRepo.save(device);
    }


    @Override
    public Collection<DeviceDTO> findAllDevices() {
        return deviceRepo.findAll().stream().map(toDeviceDTO::convert).collect(Collectors.toList());
    }

    @Override
    public Collection<LogDTO> findDeviceLogs(Long id) {
        return deviceRepo.findById(id).get().getLogs().stream().map(toLogDTO::convert).collect(Collectors.toList());
    }
}
