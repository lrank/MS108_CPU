module IF_ID(
  clk_i,
  IR_i,
  IR_o,
  isstall_i
);

input wire clk_i;
input wire [31:0] IR_i;
output reg [31:0] IR_o;
input isstall_i;

always @(posedge clk_i)
begin
  //$display("ins: %b", IR_i);
  if (isstall_i == 1'b1)
    IR_o = {4'b1110, 28'b0};
  else
    IR_o = IR_i;
end

endmodule
