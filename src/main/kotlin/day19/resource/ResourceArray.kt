package day19.resource

import utils.EnumArray

class ResourceArray<T>(transform: (Resource) -> T) : EnumArray<Resource, T>(enumValues(), transform)
