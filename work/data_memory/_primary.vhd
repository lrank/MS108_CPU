library verilog;
use verilog.vl_types.all;
entity data_memory is
    generic(
        SIZE            : integer := 262144
    );
    port(
        reset0          : in     vl_logic;
        clock           : in     vl_logic;
        data            : in     vl_logic_vector(31 downto 0);
        addr            : in     vl_logic_vector(31 downto 0);
        write_enable    : in     vl_logic;
        outp            : out    vl_logic_vector(31 downto 0);
        valid           : out    vl_logic;
        visit           : in     vl_logic
    );
    attribute mti_svvh_generic_type : integer;
    attribute mti_svvh_generic_type of SIZE : constant is 1;
end data_memory;
