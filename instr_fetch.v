module instr_fetch(
  pc,
  instr_o
  );

input wire [31:0] pc;
output reg [31:0] instr_o;

wire [31:0] instr_get;

instruction_cache instr_cache(
  .clk (clk),
  .reset  (0),
  .write  (0),
  .read (1),
  .address  (pc),
  .data_in (32'b0),
  .data_out  (instr_get),
  .hit (0)
  );  // 2-way associative, LRU replacement policy.

always @(posedge clk) begin
  instr_o = instr_get;
end

endmodule