package com.lendbiz.p2p.api.utils;

import java.io.IOException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class SavisUltils {
    // Date-time helpers

    private static final DateTimeFormatter DATE_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            .appendOptional(DateTimeFormatter.ISO_INSTANT)
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX"))
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetDateTime parseDateTimeString(String str) {
        return ZonedDateTime.from(SavisUltils.DATE_TIME_FORMATTER.parse(str)).toOffsetDateTime();
    }

    private static final DateTimeFormatter TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ISO_TIME)
            .appendOptional(DateTimeFormatter.ISO_OFFSET_TIME)
            .parseDefaulting(ChronoField.YEAR, 2020)
            .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
            .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            .toFormatter()
            .withZone(ZoneOffset.UTC);

    public static OffsetTime parseTimeString(String str) {
        return ZonedDateTime.from(SavisUltils.TIME_FORMATTER.parse(str)).toOffsetDateTime().toOffsetTime();
    }
    // Serialize/deserialize helpers

    public static IdentityFromSavisResponse fromJsonString(String json) throws IOException {
        return getObjectReader().readValue(json);
    }

    public static String toJsonString(IdentityFromSavisResponse obj) throws JsonProcessingException {
        return getObjectWriter().writeValueAsString(obj);
    }

    private static ObjectReader reader;
    private static ObjectWriter writer;

    private static void instantiateMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            @Override
            public OffsetDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                    throws IOException, JsonProcessingException {
                String value = jsonParser.getText();
                return SavisUltils.parseDateTimeString(value);
            }
        });
        mapper.registerModule(module);
        reader = mapper.readerFor(IdentityFromSavisResponse.class);
        writer = mapper.writerFor(IdentityFromSavisResponse.class);
    }

    private static ObjectReader getObjectReader() {
        if (reader == null)
            instantiateMapper();
        return reader;
    }

    private static ObjectWriter getObjectWriter() {
        if (writer == null)
            instantiateMapper();
        return writer;
    }
}

class IdentityFromSavisResponse {
    private Output[] output;
    private double time;
    private String apiVersion;
    private String mlchainVersion;
    private UUID requestID;

    @JsonProperty("output")
    public Output[] getOutput() {
        return output;
    }

    @JsonProperty("output")
    public void setOutput(Output[] value) {
        this.output = value;
    }

    @JsonProperty("time")
    public double getTime() {
        return time;
    }

    @JsonProperty("time")
    public void setTime(double value) {
        this.time = value;
    }

    @JsonProperty("api_version")
    public String getAPIVersion() {
        return apiVersion;
    }

    @JsonProperty("api_version")
    public void setAPIVersion(String value) {
        this.apiVersion = value;
    }

    @JsonProperty("mlchain_version")
    public String getMlchainVersion() {
        return mlchainVersion;
    }

    @JsonProperty("mlchain_version")
    public void setMlchainVersion(String value) {
        this.mlchainVersion = value;
    }

    @JsonProperty("request_id")
    public UUID getRequestID() {
        return requestID;
    }

    @JsonProperty("request_id")
    public void setRequestID(UUID value) {
        this.requestID = value;
    }
}

class Output {
    private DanToc hoTen;
    private DanToc ngaySinh;
    private DanToc gioiTinh;
    private Liveness liveness;
    private DanToc hoKhauThuongTru;
    private DanToc ngayHetHan;
    private DanToc danToc;
    private DanToc nguyenQuan;
    private DanToc id;
    private ClassName className;

    @JsonProperty("ho_ten")
    public DanToc getHoTen() {
        return hoTen;
    }

    @JsonProperty("ho_ten")
    public void setHoTen(DanToc value) {
        this.hoTen = value;
    }

    @JsonProperty("ngay_sinh")
    public DanToc getNgaySinh() {
        return ngaySinh;
    }

    @JsonProperty("ngay_sinh")
    public void setNgaySinh(DanToc value) {
        this.ngaySinh = value;
    }

    @JsonProperty("gioi_tinh")
    public DanToc getGioiTinh() {
        return gioiTinh;
    }

    @JsonProperty("gioi_tinh")
    public void setGioiTinh(DanToc value) {
        this.gioiTinh = value;
    }

    @JsonProperty("liveness")
    public Liveness getLiveness() {
        return liveness;
    }

    @JsonProperty("liveness")
    public void setLiveness(Liveness value) {
        this.liveness = value;
    }

    @JsonProperty("ho_khau_thuong_tru")
    public DanToc getHoKhauThuongTru() {
        return hoKhauThuongTru;
    }

    @JsonProperty("ho_khau_thuong_tru")
    public void setHoKhauThuongTru(DanToc value) {
        this.hoKhauThuongTru = value;
    }

    @JsonProperty("ngay_het_han")
    public DanToc getNgayHetHan() {
        return ngayHetHan;
    }

    @JsonProperty("ngay_het_han")
    public void setNgayHetHan(DanToc value) {
        this.ngayHetHan = value;
    }

    @JsonProperty("dan_toc")
    public DanToc getDanToc() {
        return danToc;
    }

    @JsonProperty("dan_toc")
    public void setDanToc(DanToc value) {
        this.danToc = value;
    }

    @JsonProperty("nguyen_quan")
    public DanToc getNguyenQuan() {
        return nguyenQuan;
    }

    @JsonProperty("nguyen_quan")
    public void setNguyenQuan(DanToc value) {
        this.nguyenQuan = value;
    }

    @JsonProperty("id")
    public DanToc getID() {
        return id;
    }

    @JsonProperty("id")
    public void setID(DanToc value) {
        this.id = value;
    }

    @JsonProperty("class_name")
    public ClassName getClassName() {
        return className;
    }

    @JsonProperty("class_name")
    public void setClassName(ClassName value) {
        this.className = value;
    }
}

class ClassName {
    private ClassNameNormalized normalized;
    private long confidence;
    private String value;

    @JsonProperty("normalized")
    public ClassNameNormalized getNormalized() {
        return normalized;
    }

    @JsonProperty("normalized")
    public void setNormalized(ClassNameNormalized value) {
        this.normalized = value;
    }

    @JsonProperty("confidence")
    public long getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(long value) {
        this.confidence = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }
}

// ClassNameNormalized.java

class ClassNameNormalized {
    private String code;
    private long value;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String value) {
        this.code = value;
    }

    @JsonProperty("value")
    public long getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(long value) {
        this.value = value;
    }
}

// DanToc.java

class DanToc {
    private double confidence;
    private String valueUnidecode;
    private String value;
    private DanTocNormalized normalized;
    private Validate validate;

    @JsonProperty("confidence")
    public double getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(double value) {
        this.confidence = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    @JsonProperty("normalized")
    public DanTocNormalized getNormalized() {
        return normalized;
    }

    @JsonProperty("normalized")
    public void setNormalized(DanTocNormalized value) {
        this.normalized = value;
    }

    @JsonProperty("validate")
    public Validate getValidate() {
        return validate;
    }

    @JsonProperty("validate")
    public void setValidate(Validate value) {
        this.validate = value;
    }
}

// DanTocNormalized.java

class DanTocNormalized {
    private Value value;
    private Huyen huyen;
    private String valueUnidecode;
    private Huyen tinh;
    private Xa xa;
    private Day month;
    private Day year;
    private Day day;

    @JsonProperty("value")
    public Value getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(Value value) {
        this.value = value;
    }

    @JsonProperty("huyen")
    public Huyen getHuyen() {
        return huyen;
    }

    @JsonProperty("huyen")
    public void setHuyen(Huyen value) {
        this.huyen = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("tinh")
    public Huyen getTinh() {
        return tinh;
    }

    @JsonProperty("tinh")
    public void setTinh(Huyen value) {
        this.tinh = value;
    }

    @JsonProperty("xa")
    public Xa getXa() {
        return xa;
    }

    @JsonProperty("xa")
    public void setXa(Xa value) {
        this.xa = value;
    }

    @JsonProperty("month")
    public Day getMonth() {
        return month;
    }

    @JsonProperty("month")
    public void setMonth(Day value) {
        this.month = value;
    }

    @JsonProperty("year")
    public Day getYear() {
        return year;
    }

    @JsonProperty("year")
    public void setYear(Day value) {
        this.year = value;
    }

    @JsonProperty("day")
    public Day getDay() {
        return day;
    }

    @JsonProperty("day")
    public void setDay(Day value) {
        this.day = value;
    }
}

// Day.java

class Day {
    private long value;

    @JsonProperty("value")
    public long getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(long value) {
        this.value = value;
    }
}

// Huyen.java

class Huyen {
    private Value code;
    private String valueUnidecode;
    private String value;

    @JsonProperty("code")
    public Value getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Value value) {
        this.code = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }
}

// Value.java

@JsonDeserialize(using = Value.Deserializer.class)
@JsonSerialize(using = Value.Serializer.class)
class Value {
    public Long integerValue;
    public String stringValue;

    static class Deserializer extends JsonDeserializer<Value> {
        @Override
        public Value deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
                throws IOException, JsonProcessingException {
            Value value = new Value();
            switch (jsonParser.currentToken()) {
                case VALUE_NUMBER_INT:
                    value.integerValue = jsonParser.readValueAs(Long.class);
                    break;
                case VALUE_STRING:
                    String string = jsonParser.readValueAs(String.class);
                    value.stringValue = string;
                    break;
                default:
                    throw new IOException("Cannot deserialize Value");
            }
            return value;
        }
    }

    static class Serializer extends JsonSerializer<Value> {
        @Override
        public void serialize(Value obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
                throws IOException {
            if (obj.integerValue != null) {
                jsonGenerator.writeObject(obj.integerValue);
                return;
            }
            if (obj.stringValue != null) {
                jsonGenerator.writeObject(obj.stringValue);
                return;
            }
            throw new IOException("Value must not be null");
        }
    }
}

// Xa.java

class Xa {
    private long code;
    private String valueUnidecode;
    private String value;

    @JsonProperty("code")
    public long getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(long value) {
        this.code = value;
    }

    @JsonProperty("value_unidecode")
    public String getValueUnidecode() {
        return valueUnidecode;
    }

    @JsonProperty("value_unidecode")
    public void setValueUnidecode(String value) {
        this.valueUnidecode = value;
    }

    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }
}

// Validate.java

class Validate {
    private double[] idconf;
    private long idLogic;
    private String idLogicMessage;
    private String idCheck;

    @JsonProperty("idconf")
    public double[] getIdconf() {
        return idconf;
    }

    @JsonProperty("idconf")
    public void setIdconf(double[] value) {
        this.idconf = value;
    }

    @JsonProperty("id_logic")
    public long getIDLogic() {
        return idLogic;
    }

    @JsonProperty("id_logic")
    public void setIDLogic(long value) {
        this.idLogic = value;
    }

    @JsonProperty("id_logic_message")
    public String getIDLogicMessage() {
        return idLogicMessage;
    }

    @JsonProperty("id_logic_message")
    public void setIDLogicMessage(String value) {
        this.idLogicMessage = value;
    }

    @JsonProperty("id_check")
    public String getIDCheck() {
        return idCheck;
    }

    @JsonProperty("id_check")
    public void setIDCheck(String value) {
        this.idCheck = value;
    }
}

// Liveness.java

class Liveness {
    private String liveness;

    @JsonProperty("liveness")
    public String getLiveness() {
        return liveness;
    }

    @JsonProperty("liveness")
    public void setLiveness(String value) {
        this.liveness = value;
    }
}
