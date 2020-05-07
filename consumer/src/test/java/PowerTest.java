import java.util.Random;

public class PowerTest {

    public static void main(String[] args) {
        int x = 3, n = 8;
        System.out.println(Math.pow(x, n));
        System.out.println(power1(x, n));
        System.out.println(powerRecursion(x, n));
        System.out.println(powerLoop(x, n));
        int a = new Random().nextInt(100);
        System.out.println(a + "=>" + ((a >> 1 << 1) == a));
    }

    public static double power1(int x, int n) {
        int result = 1;
        for (int i = 1; i <= Math.abs(n); i++) {
            result = result * x;
        }
        return n > 0 ? result : 1d / result;
    }

    /**
     * 递归
     * 例如：我们想求3的8次幂是多少，3^8=?
     * 思路：我们可以将问题拆分，转换为
     * 3^8 = (3^4) * (3^4)
     * = (3^2 * 3^2) * (3^2 * 3^2)
     * = (3 * 3) * (3 * 3) * (3 * 3) * (3 * 3)
     * 偶数：
     * f(3,8)
     * => f(9, 8/2)
     * => f(81, 4/2)
     * => f(6561, 2/2)
     * => 6561 * f(6561 * 6561, 1/2) 返回
     * 奇数：
     * f(3,9)  因为奇数只比偶数多出一个底数。所以减去一个底数按偶数计算，最后把偶数算出来的结果在乘上一个底数即可
     * => 3 * f(9, (9-1)/2)
     * => f(81, 4/2)
     * 其余与偶数一致....
     *
     * @param x 底数
     * @param n 次幂
     * @return 计算结果
     */
    public static double powerRecursion(int x, int n) {
        // 如果n为0，则直接返回1。例如：2^0=1；3^0=1依次类推
        if (n == 0) {
            return 1;
        }
        // 如果n为负数，则转为倒数。例如：2^-2=1/4；3^-3=1/27以此类推
        if (n < 0) {
            return 1 / powerRecursion(x, Math.abs(n));
        }
        // 如果n为奇数，n%2==1 则为奇数，或者使用 n&1 == 1 也可以，(n >> 1 << 1) == n 就是看数字的二级制最后一位是否是1
        if (n % 2 == 1) {
            // 如果是奇数的话
            return x * powerRecursion(x * x, (n - 1) / 2);
        }
        // 偶数
        return powerRecursion(x * x, n / 2);
    }

    /**
     * 循环
     * 如果我们求2^5的话，表示成二级制为00100000。 2^0<<5向左移动5位 , 00000001 => 00100000
     * 00100000 = (0*2^7) + (0*2^6) + (1*2^5) + (0*2^4) + (0*2^3) + (0*2^2) + (0*2^1) + (0*2^0)
     * 求3的5次幂 3^5 = ?
     * 思路：将3^5进行拆分 3^5 = 3^4 * 3^1
     * <p>
     * 首先将5转换成2进制 = 00000101 将二进制拆分成多个2的等次幂。即二进制位只能存在一个1.
     * 00000101 = 00000100 + 00000001;
     * 怎么拆分呢？只要判断二级制末尾是否为1即可，如果为1则将结果相乘。 计算完成后，对二进制位再向右位移1位，依次进行。
     * .......第4位=>3^16、第3位=>3^8、第2位=>3^4、第1位=>3^2、第0位=>3^1
     * 由于5对应的二进制上只有第0、2位上是1，所以将0、2位对应的值相乘。即3^4*3^1
     *
     * @param x 底数
     * @param n 次幂
     * @return 结果
     */
    public static double powerLoop(int x, int n) {
        // 如果小于0求倒数
        if (n < 0) {
            x = 1 / x;
            n = -n; // n = Math.abs(n)
        }
        // 声明结果
        double result = 1;
        while (n != 0) {
            if ((n & 1) != 0) {
                result *= x;
            }
            x *= x;
            n >>= 1;
        }
        return result;
    }
}
