module instr_fetch(
  clk,
  pc,
  instr_o,
  end_signal_o
  );

input wire clk;
input wire [31:0] pc;
output reg [31:0] instr_o;
output reg end_signal_o;

//input wire isstall_i;
/*
wire [31:0] instr_get;

instruction_memory instr_cache(
  .clock (clk),
  .reset0  (1'b0),
  .write_enable  (1'b0),
  .addr  (pc),
  .data (32'b0),
  .outp  (instr_get),
  .valid()
  );  // 2-way associative, LRU replacement policy.
*/

parameter SIZE=262144;
reg [31:0] mem [0:SIZE-1];

initial begin
  $readmemb("instr.txt", mem, 0);
  end_signal_o = 1'b0;
end

always @(pc/* or isstall_i*/)
begin
  /*if (isstall_i == 1'b1)
    instr_o = {4'b1110, 28'b0};
  else*/
  
  instr_o = mem[pc];
  if (instr_o[31:28] == 4'b1111)
  begin
    end_signal_o = 1'b1;
  end
end

endmodule