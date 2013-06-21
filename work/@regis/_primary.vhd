library verilog;
use verilog.vl_types.all;
entity Regis is
    generic(
        NIB_SIZE        : integer := 4;
        BYTE_SIZE       : integer := 8;
        WORD_SIZE       : integer := 16;
        MEM_SIZE        : integer := 4096;
        ALU_LW          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi0, Hi0);
        ALU_SW          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi0, Hi1);
        ALU_LI          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi1, Hi0);
        ALU_ADDU        : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi1, Hi1);
        ALU_ADDIU       : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi0, Hi0);
        ALU_SLL         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi0, Hi1);
        ALU_MUL         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi1, Hi0);
        ALU_BGE         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi1, Hi1);
        ALU_J           : vl_logic_vector(0 to 3) := (Hi1, Hi0, Hi0, Hi0);
        ALU_MULI        : vl_logic_vector(0 to 3) := (Hi1, Hi0, Hi0, Hi1);
        OP_ADD          : vl_logic_vector(0 to 2) := (Hi0, Hi0, Hi0);
        OP_MUL          : vl_logic_vector(0 to 2) := (Hi0, Hi0, Hi1);
        OP_SLL          : vl_logic_vector(0 to 2) := (Hi0, Hi1, Hi0);
        OP_BGE          : vl_logic_vector(0 to 2) := (Hi0, Hi1, Hi1)
    );
    port(
        IR_i            : in     vl_logic_vector(31 downto 0);
        writenum_i      : in     vl_logic_vector(5 downto 0);
        writedata_i     : in     vl_logic_vector(31 downto 0);
        data1_o         : out    vl_logic_vector(31 downto 0);
        data2_o         : out    vl_logic_vector(31 downto 0)
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of NIB_SIZE : constant is 1;
    attribute mti_svvh_generic_type of BYTE_SIZE : constant is 1;
    attribute mti_svvh_generic_type of WORD_SIZE : constant is 1;
    attribute mti_svvh_generic_type of MEM_SIZE : constant is 1;
    attribute mti_svvh_generic_type of ALU_LW : constant is 1;
    attribute mti_svvh_generic_type of ALU_SW : constant is 1;
    attribute mti_svvh_generic_type of ALU_LI : constant is 1;
    attribute mti_svvh_generic_type of ALU_ADDU : constant is 1;
    attribute mti_svvh_generic_type of ALU_ADDIU : constant is 1;
    attribute mti_svvh_generic_type of ALU_SLL : constant is 1;
    attribute mti_svvh_generic_type of ALU_MUL : constant is 1;
    attribute mti_svvh_generic_type of ALU_BGE : constant is 1;
    attribute mti_svvh_generic_type of ALU_J : constant is 1;
    attribute mti_svvh_generic_type of ALU_MULI : constant is 1;
    attribute mti_svvh_generic_type of OP_ADD : constant is 1;
    attribute mti_svvh_generic_type of OP_MUL : constant is 1;
    attribute mti_svvh_generic_type of OP_SLL : constant is 1;
    attribute mti_svvh_generic_type of OP_BGE : constant is 1;
end Regis;
