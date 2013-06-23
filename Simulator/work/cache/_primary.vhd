library verilog;
use verilog.vl_types.all;
entity cache is
    generic(
        DATAWIDTH       : integer := 32;
        INDEX           : integer := 12;
        SIZE            : integer := 54
    );
    port(
        clock           : in     vl_logic;
        reset           : in     vl_logic;
        write           : in     vl_logic;
        read            : in     vl_logic;
        address         : in     vl_logic_vector;
        data_in         : in     vl_logic_vector;
        data_out        : out    vl_logic_vector;
        hit             : out    vl_logic;
        end_signal_i    : in     vl_logic
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of DATAWIDTH : constant is 1;
    attribute mti_svvh_generic_type of INDEX : constant is 1;
    attribute mti_svvh_generic_type of SIZE : constant is 1;
end cache;
