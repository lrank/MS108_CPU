library verilog;
use verilog.vl_types.all;
entity MEM is
    port(
        clk             : in     vl_logic;
        address_i       : in     vl_logic_vector(31 downto 0);
        data_i          : in     vl_logic_vector(31 downto 0);
        row_i           : in     vl_logic_vector(1 downto 0);
        data_o          : out    vl_logic_vector(31 downto 0)
    );
end MEM;
