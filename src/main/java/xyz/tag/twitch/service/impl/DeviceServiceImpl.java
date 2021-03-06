package xyz.tag.twitch.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import xyz.tag.twitch.dto.DeviceDTO;
import xyz.tag.twitch.dto.LogDTO;
import xyz.tag.twitch.dto.LogsMapper;
import xyz.tag.twitch.dto.RollingLogDTO;
import xyz.tag.twitch.dto.electrodev.Req;
import xyz.tag.twitch.dto.electrodev.Resp;
import xyz.tag.twitch.entity.Device;
import xyz.tag.twitch.entity.Log;
import xyz.tag.twitch.entity.RespHealthCheckDO;
import xyz.tag.twitch.enums.EStatus;
import xyz.tag.twitch.enums.ESwitch;
import xyz.tag.twitch.exception.DeviceNotFoundException;
import xyz.tag.twitch.feign.ElectroDeviceFeignService;
import xyz.tag.twitch.repo.DeviceRepo;
import xyz.tag.twitch.repo.RespHealthCheckRepo;
import xyz.tag.twitch.service.DeviceService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
    private ElectroDeviceFeignService electroDeviceFeignService;
    private final DeviceRepo deviceRepo;
    private Converter<Device, DeviceDTO> toDeviceDTO;
    private Converter<Log, LogDTO> toLogDTO;
    private Converter<LogsMapper, Collection<RollingLogDTO>> toRollingLogsDTO;
    private RespHealthCheckRepo healthCheckRepo;

    public DeviceServiceImpl(DeviceRepo deviceRepo) {
        this.deviceRepo = deviceRepo;
    }

    @Autowired
    public void setToDeviceDTO(Converter<Device, DeviceDTO> toDeviceDTO) {
        this.toDeviceDTO = toDeviceDTO;
    }

    @Autowired
    public void setToLogDTO(Converter<Log, LogDTO> toLogDTO) {
        this.toLogDTO = toLogDTO;
    }

    @Autowired
    public void setElectroDeviceFeignService(ElectroDeviceFeignService electroDeviceFeignService) {
        this.electroDeviceFeignService = electroDeviceFeignService;
    }

    @Autowired
    public void setHealthCheckRepo(RespHealthCheckRepo healthCheckRepo) {
        this.healthCheckRepo = healthCheckRepo;
    }

    @Autowired
    public void setToRollingLogsDTO(@Qualifier("RollingDTOConverter") Converter toRollingLogsDTO) {
        this.toRollingLogsDTO = toRollingLogsDTO;
    }

    /**
     * Turns the switch Onn or Off
     *
     * @param id     of the Device in question.
     * @param option To indicate the operation applied to the switch.
     */
    @Override
    public void toggleSwitch(Long id, ESwitch option) throws DeviceNotFoundException {
        Optional<Device> optionalDevice = deviceRepo.findById(id);
        final Device device = optionalDevice.orElseThrow(() -> new DeviceNotFoundException("Device ID: " + id + " not found."));
        final Boolean b = EStatus.ONLINE.getStatus().equals(option.getStatus());
        device.setOnn(b);
        EStatus status = EStatus.ONLINE;

        final Req req = new Req(option);

        LOGGER.info("HTTP POST Req: {}", req);
        Resp resp = new Resp();
        final Log log = new Log(option, status, ZonedDateTime.now(), null);
        try {
            final long channel = Long.parseLong(device.getChannel());

            LOGGER.info("Extracted Device-Channel: {}", channel);

            resp = electroDeviceFeignService.invokeSwitch(channel, req);
            if (HttpStatus.OK.value() == resp.getCode()) {
                log.setEStatus(EStatus.ONLINE);
            } else {
                log.setEStatus(EStatus.OFFLINE);
            }
        } catch (Exception e) {
            LOGGER.error("HTTP Exception: {}", e.getMessage(), e);
            device.setOnn(false);
            log.setEStatus(EStatus.UNREACHABLE);
            log.setESwitch(ESwitch.OFF);
        }
        log.setRespDateTime(ZonedDateTime.now());
        LOGGER.info("HTTP POST Response: {}", resp);
        device.getLogs().add(log);
        deviceRepo.save(device);
    }

    @Override
    public Collection<DeviceDTO> findAllDevices() {
        return deviceRepo.findAll().stream().map(toDeviceDTO::convert).collect(Collectors.toList());
    }

    @Override
    public Collection<LogDTO> findDeviceLogs(Long id) {
        final Device newDevice = deviceRepo.findById(id).orElse(null);
        return newDevice != null ? newDevice.getLogs().parallelStream().map(toLogDTO::convert).collect(Collectors.toList()) : null;
    }

    @Override
    public Collection<RollingLogDTO> meshUpRollingLogs() {
        final Collection<RespHealthCheckDO> deviceLogs = healthCheckRepo.findAll();     // Device Ping Health Logs.
        final Collection<Log> opLogs = new ArrayList<>();   //Channel Operational Logs.
        deviceRepo.findAll().parallelStream().map(Device::getLogs).forEach(opLogs::addAll);
        List<RollingLogDTO> rollingLogs = (List<RollingLogDTO>) toRollingLogsDTO.convert(new LogsMapper(opLogs, deviceLogs));
        Objects.requireNonNull(rollingLogs).parallelStream().filter(rLog -> Objects.nonNull(rLog) && Objects.nonNull(rLog.getDeviceId())).forEach(rLog -> {
            final Device device = deviceRepo.findByLogsId(rLog.getDeviceId());
            final String itemName = new StringBuilder().append(device.getLocation()).append(" ").append(device.getType()).toString();
            rLog.setItem(itemName);
        });

        return rollingLogs;
    }
}
