module PC
(
    clk_i,
    control_i,
    pc_i,
    pc_o
);

input clk_i;
input [1:0] control_i;
input [31:0] pc_i;

output reg [31:0] pc_o;

reg [31:0] pc;

initial
begin
   pc = 32'b0;
end

MUX32 MUX1(
  .data2  (pc+1),
  .data3  (pc_i),
  .select (control_i),
  .pc_o   (pc)
);

always@(posedge clk_i)
begin
  pc <= MUX1.pc_o;
  pc_o <= MUX1.pc_o;
end

endmodule

