package io.openmico.beans;

public class DeviceBean {

    private String deviceID;

    private String serialNumber;

    private String name;

    private String alias;

    private boolean current;

    private String presence;

    private String address;

    private String miotDID;

    private String hardware;

    private String romVersion;

    private Capabilities capabilities;

    private String remoteCtrlType;

    private String deviceSNProfile;

    private String deviceProfile;

    public void setDeviceID(String deviceID){
        this.deviceID = deviceID;
    }
    public String getDeviceID(){
        return this.deviceID;
    }
    public void setSerialNumber(String serialNumber){
        this.serialNumber = serialNumber;
    }
    public String getSerialNumber(){
        return this.serialNumber;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setAlias(String alias){
        this.alias = alias;
    }
    public String getAlias(){
        return this.alias;
    }
    public void setCurrent(boolean current){
        this.current = current;
    }
    public boolean getCurrent(){
        return this.current;
    }
    public void setPresence(String presence){
        this.presence = presence;
    }
    public String getPresence(){
        return this.presence;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
    public void setMiotDID(String miotDID){
        this.miotDID = miotDID;
    }
    public String getMiotDID(){
        return this.miotDID;
    }
    public void setHardware(String hardware){
        this.hardware = hardware;
    }
    public String getHardware(){
        return this.hardware;
    }
    public void setRomVersion(String romVersion){
        this.romVersion = romVersion;
    }
    public String getRomVersion(){
        return this.romVersion;
    }
    public void setCapabilities(Capabilities capabilities){
        this.capabilities = capabilities;
    }
    public Capabilities getCapabilities(){
        return this.capabilities;
    }
    public void setRemoteCtrlType(String remoteCtrlType){
        this.remoteCtrlType = remoteCtrlType;
    }
    public String getRemoteCtrlType(){
        return this.remoteCtrlType;
    }
    public void setDeviceSNProfile(String deviceSNProfile){
        this.deviceSNProfile = deviceSNProfile;
    }
    public String getDeviceSNProfile(){
        return this.deviceSNProfile;
    }
    public void setDeviceProfile(String deviceProfile){
        this.deviceProfile = deviceProfile;
    }
    public String getDeviceProfile(){
        return this.deviceProfile;
    }
    public class Capabilities {
        private int content_blacklist;

        private int school_timetable;

        private int user_nick_name;

        private int player_pause_timer;

        private int child_mode_2;

        private int voice_print;

        private int voip_signal;

        private int ai_instruction;

        private int classified_alarm;

        private int mesh;

        private int ai_protocol_3_0;

        private int voice_print_new;

        private int child_mode;

        private int voice_print_multidevice;

        private int baby_schedule;

        private int tone_setting;

        private int family_album;

        private int earthquake;

        private int video_call;

        private int alarm_repeat_option_v2;

        private int address_book;

        private int xiaomi_voip;

        private int nearby_wakeup_cloud;

        private int continuous_dialogue;

        private int skill_try;

        private int mico_current;

        private int screen_mode;

        private int voip_used_time;

        public void setContent_blacklist(int content_blacklist){
            this.content_blacklist = content_blacklist;
        }
        public int getContent_blacklist(){
            return this.content_blacklist;
        }
        public void setSchool_timetable(int school_timetable){
            this.school_timetable = school_timetable;
        }
        public int getSchool_timetable(){
            return this.school_timetable;
        }
        public void setUser_nick_name(int user_nick_name){
            this.user_nick_name = user_nick_name;
        }
        public int getUser_nick_name(){
            return this.user_nick_name;
        }
        public void setPlayer_pause_timer(int player_pause_timer){
            this.player_pause_timer = player_pause_timer;
        }
        public int getPlayer_pause_timer(){
            return this.player_pause_timer;
        }
        public void setChild_mode_2(int child_mode_2){
            this.child_mode_2 = child_mode_2;
        }
        public int getChild_mode_2(){
            return this.child_mode_2;
        }
        public void setVoice_print(int voice_print){
            this.voice_print = voice_print;
        }
        public int getVoice_print(){
            return this.voice_print;
        }
        public void setVoip_signal(int voip_signal){
            this.voip_signal = voip_signal;
        }
        public int getVoip_signal(){
            return this.voip_signal;
        }
        public void setAi_instruction(int ai_instruction){
            this.ai_instruction = ai_instruction;
        }
        public int getAi_instruction(){
            return this.ai_instruction;
        }
        public void setClassified_alarm(int classified_alarm){
            this.classified_alarm = classified_alarm;
        }
        public int getClassified_alarm(){
            return this.classified_alarm;
        }
        public void setMesh(int mesh){
            this.mesh = mesh;
        }
        public int getMesh(){
            return this.mesh;
        }
        public void setAi_protocol_3_0(int ai_protocol_3_0){
            this.ai_protocol_3_0 = ai_protocol_3_0;
        }
        public int getAi_protocol_3_0(){
            return this.ai_protocol_3_0;
        }
        public void setVoice_print_new(int voice_print_new){
            this.voice_print_new = voice_print_new;
        }
        public int getVoice_print_new(){
            return this.voice_print_new;
        }
        public void setChild_mode(int child_mode){
            this.child_mode = child_mode;
        }
        public int getChild_mode(){
            return this.child_mode;
        }
        public void setVoice_print_multidevice(int voice_print_multidevice){
            this.voice_print_multidevice = voice_print_multidevice;
        }
        public int getVoice_print_multidevice(){
            return this.voice_print_multidevice;
        }
        public void setBaby_schedule(int baby_schedule){
            this.baby_schedule = baby_schedule;
        }
        public int getBaby_schedule(){
            return this.baby_schedule;
        }
        public void setTone_setting(int tone_setting){
            this.tone_setting = tone_setting;
        }
        public int getTone_setting(){
            return this.tone_setting;
        }
        public void setFamily_album(int family_album){
            this.family_album = family_album;
        }
        public int getFamily_album(){
            return this.family_album;
        }
        public void setEarthquake(int earthquake){
            this.earthquake = earthquake;
        }
        public int getEarthquake(){
            return this.earthquake;
        }
        public void setVideo_call(int video_call){
            this.video_call = video_call;
        }
        public int getVideo_call(){
            return this.video_call;
        }
        public void setAlarm_repeat_option_v2(int alarm_repeat_option_v2){
            this.alarm_repeat_option_v2 = alarm_repeat_option_v2;
        }
        public int getAlarm_repeat_option_v2(){
            return this.alarm_repeat_option_v2;
        }
        public void setAddress_book(int address_book){
            this.address_book = address_book;
        }
        public int getAddress_book(){
            return this.address_book;
        }
        public void setXiaomi_voip(int xiaomi_voip){
            this.xiaomi_voip = xiaomi_voip;
        }
        public int getXiaomi_voip(){
            return this.xiaomi_voip;
        }
        public void setNearby_wakeup_cloud(int nearby_wakeup_cloud){
            this.nearby_wakeup_cloud = nearby_wakeup_cloud;
        }
        public int getNearby_wakeup_cloud(){
            return this.nearby_wakeup_cloud;
        }
        public void setContinuous_dialogue(int continuous_dialogue){
            this.continuous_dialogue = continuous_dialogue;
        }
        public int getContinuous_dialogue(){
            return this.continuous_dialogue;
        }
        public void setSkill_try(int skill_try){
            this.skill_try = skill_try;
        }
        public int getSkill_try(){
            return this.skill_try;
        }
        public void setMico_current(int mico_current){
            this.mico_current = mico_current;
        }
        public int getMico_current(){
            return this.mico_current;
        }
        public void setScreen_mode(int screen_mode){
            this.screen_mode = screen_mode;
        }
        public int getScreen_mode(){
            return this.screen_mode;
        }
        public void setVoip_used_time(int voip_used_time){
            this.voip_used_time = voip_used_time;
        }
        public int getVoip_used_time(){
            return this.voip_used_time;
        }

    }

}



