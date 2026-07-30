import java.sql.Connection;

/**
 * کلاس پایه (abstract) برای همه‌ی کلاس‌های DAO پروژه.
 *
 * هدف این کلاس فقط یک چیزه: جلوگیری از تکرار خط
 * "DatabaseConnection.getConnection()" در تک‌تک DAO ها،
 * و در عین حال نشون دادن استفاده‌ی واقعی از وراثت (inheritance)
 * در معماری پروژه — هر DAO از این کلاس ارث می‌بره و متد
 * getConnection() رو مستقیماً از پدرش می‌گیره.
 *
 * این کلاس abstract است، یعنی نمی‌شه مستقیماً از آن شیء ساخت
 * (new BaseDAO() مجاز نیست)؛ فقط کلاس‌های فرزند مثل RecipeDAO
 * یا UserDAO می‌توانند از آن استفاده کنند.
 */
public abstract class BaseDAO {

    /**
     * یک اتصال جدید به دیتابیس برمی‌گرداند.
     * تمام کلاس‌های فرزند به‌جای نوشتن مستقیم
     * DatabaseConnection.getConnection() همین متد ارث‌بری‌شده را صدا می‌زنند.
     */
    protected Connection getConnection() {
        return DatabaseConnection.getConnection();
    }
}
