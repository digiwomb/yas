package dev.digiwomb.yas.model.uuid

import com.fasterxml.uuid.Generators
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator
import java.io.Serializable

class UuidV7GeneratorType : IdentifierGenerator {

    override fun generate(session: SharedSessionContractImplementor?, obj: Any?): Serializable {
        return Generators.timeBasedEpochGenerator().generate()
    }

    override fun allowAssignedIdentifiers(): Boolean {
        return true;
    }
}
