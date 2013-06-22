library verilog;
use verilog.vl_types.all;
entity bypass is
    generic(
        ALU_LW          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi0, Hi0);
        ALU_SW          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi0, Hi1);
        ALU_LI          : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi1, Hi0);
        ALU_ADDU        : vl_logic_vector(0 to 3) := (Hi0, Hi0, Hi1, Hi1);
        ALU_ADDIU       : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi0, Hi0);
        ALU_SLL         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi0, Hi1);
        ALU_MUL         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi1, Hi0);
        ALU_BGE         : vl_logic_vector(0 to 3) := (Hi0, Hi1, Hi1, Hi1);
        ALU_J           : vl_logic_vector(0 to 3) := (Hi1, Hi0, Hi0, Hi0);
        ALU_MULI        : vl_logic_vector(0 to 3) := (Hi1, Hi0, Hi0, Hi1)
    );
    port(
        ir1_i           : in     vl_logic_vector(31 downto 0);
        ir2_i           : in     vl_logic_vector(31 downto 0);
        data1_i         : in     vl_logic_vector(31 downto 0);
        is_o            : out    vl_logic_vector(1 downto 0);
        data_o          : out    vl_logic_vector(31 downto 0)
    );
    attribute mti_svvh_generic_type : integer;
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
end bypass;
