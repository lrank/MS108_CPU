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

MUX32 MUX1(
  .data1_i  (pc+1),
  .data2_i  (pc_i),
  .select_i (control_i),
  .data_o   (save_pc)
);

reg [31:0] pc_before;

always@(posedge clk_i)
begin
  pc=save_pc;
  if (isstall == 1'b1)begin
    pc = pc_before;
    pc_o = pc;
  end
  else begin
    pc_o = pc;
    pc_before = pc;
  end
end

endmodule

