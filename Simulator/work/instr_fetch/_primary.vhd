library verilog;
use verilog.vl_types.all;
entity instr_fetch is
    generic(
        SIZE            : integer := 262144
    );
    port(
        clk             : in     vl_logic;
        pc              : in     vl_logic_vector(31 downto 0);
        instr_o         : out    vl_logic_vector(31 downto 0);
        end_signal_o    : out    vl_logic
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of SIZE : constant is 1;
end instr_fetch;
