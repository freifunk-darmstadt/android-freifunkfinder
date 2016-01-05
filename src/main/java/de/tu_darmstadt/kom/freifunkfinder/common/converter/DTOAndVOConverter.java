package de.tu_darmstadt.kom.freifunkfinder.common.converter;

import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointDTO;
import de.tu_darmstadt.kom.freifunkfinder.common.WifiAccessPointVO;

/**
 * Created by govind on 12/17/2015.
 */
public class DTOAndVOConverter implements ConverterInt<WifiAccessPointDTO, WifiAccessPointVO> {

    private static DTOAndVOConverter DTOAndVOConverter = null;

    // singleton converter
    private DTOAndVOConverter(){

    }

    public static DTOAndVOConverter getDTOAndVOConverter(){
        if(DTOAndVOConverter == null){
            DTOAndVOConverter = new DTOAndVOConverter();
        }
        return DTOAndVOConverter;
    }


    @Override
    public WifiAccessPointVO serialize(WifiAccessPointDTO wifiAccessPointDTO) throws Exception {
        WifiAccessPointVO wifiAccessPointVO = new WifiAccessPointVO();
        wifiAccessPointVO.setNodeId(wifiAccessPointDTO.getNodeId());
        wifiAccessPointVO.setHostName(wifiAccessPointDTO.getHostName());
        //wifiAccessPointVO.setLatitude(wifiAccessPointDTO.getLatitude());
        //wifiAccessPointVO.setLongitude(wifiAccessPointDTO.getLongitude());
        //wifiAccessPointVO.setAltitude(wifiAccessPointDTO.getAltitude());
        return wifiAccessPointVO;
    }

    @Override
    public WifiAccessPointDTO deSerialize(WifiAccessPointVO wifiAccessPointVO) throws Exception {
        WifiAccessPointDTO wifiAccessPointDTO = new WifiAccessPointDTO();
        wifiAccessPointDTO.setNodeId(wifiAccessPointVO.getNodeId());
        wifiAccessPointDTO.setHostName(wifiAccessPointVO.getHostName());
        //wifiAccessPointDTO.setLatitude(wifiAccessPointVO.getLatitude());
        //wifiAccessPointDTO.setLongitude(wifiAccessPointVO.getLongitude());
        //wifiAccessPointDTO.setAltitude(wifiAccessPointVO.getAltitude());
        return wifiAccessPointDTO;
    }
}
