/*
 * MIT License
 *
 * Copyright (c) 2019-2020 MCParkour
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.mcparkour.unifig;

import java.util.Objects;
import net.mcparkour.unifig.annotation.Configuration;
import org.bukkit.util.Vector;

@Configuration("paper-configuration")
public class TestPaperConfiguration {

	private Vector vector;

	public TestPaperConfiguration() {
		this.vector = new Vector(1.11, 2.22, 3.33);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof TestPaperConfiguration)) {
			return false;
		}
		TestPaperConfiguration that = (TestPaperConfiguration) o;
		return Objects.equals(this.vector, that.vector);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.vector);
	}

	@Override
	public String toString() {
		return "TestPaperConfiguration{" +
			"vector=" + this.vector +
			'}';
	}

	public Vector getVector() {
		return this.vector;
	}
}
