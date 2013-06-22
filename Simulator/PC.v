`timescale 1ns/10ps
module PC
(
    clk_i,
    control_i,
    pc_i,
    pc_o,
    isstall
);

input clk_i;
input control_i;
input [31:0] pc_i;

output reg [31:0] pc_o;
input wire isstall;

reg [31:0] pc;
wire [31:0] save_pc;

initial
begin
   //assign pc = 32'b0;
end

initial
fork
  pc=32'b11111111111111111111111111111111;
  //pc=32'b0;
join
/*
MUX32 MUX1(
  .data1_i  (pc+1),
  .data2_i  (pc_i),
  .select_i (control_i),
  .data_o   (save_pc)
);
*/
reg [31:0] pc_before;

always@(posedge clk_i or isstall)
begin
  //pc=save_pc;
  if (isstall == 1'b1)begin
    pc = pc_before;
    pc_o = pc;
  end
  else begin
    pc_before = pc;
    if (control_i == 1'b1)
      begin
        pc = pc_i;
        pc_o = pc_i;
      end
    else begin
        pc = pc + 1'b1;
        pc_o = pc;
      end
  end
  //$display("pc : %b",pc);
  /*if (pc > 30)
    $finish;*/
end

endmodule

