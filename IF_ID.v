module IF_ID(
  clk_i,
  IR_i,
  IR_o
);

input wire clk_i;
input wire [31:0] IR_i;
output reg [31:0] IR_o;

always @(posedge clk_i)
begin
  assign IR_o = IR_i;
end

endmodule
