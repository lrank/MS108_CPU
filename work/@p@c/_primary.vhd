library verilog;
use verilog.vl_types.all;
entity PC is
    port(
        clk_i           : in     vl_logic;
        control_i       : in     vl_logic;
        pc_i            : in     vl_logic_vector(31 downto 0);
        pc_o            : out    vl_logic_vector(31 downto 0);
        isstall         : in     vl_logic
    );
end PC;
