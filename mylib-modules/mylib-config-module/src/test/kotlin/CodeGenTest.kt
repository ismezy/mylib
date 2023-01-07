import com.zy.mylib.codegen.CodeGen
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class CodeGenTest {
  @Test
  @Disabled
  fun gen() = CodeGen.run("global-config", srcPath = System.getProperty("user.dir") + "/src/main/kotlin")
}